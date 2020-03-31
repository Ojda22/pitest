/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.pitest.classinfo.ClassName;

/**
 * Uniquely identifies a mutation
 */
public final class MutationIdentifier implements Comparable<MutationIdentifier>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The location at which the mutation occurs
   */
  private final Location      location;

  /**
   * The locations at which the mutation occurs
   */
  private final List<Location> locations;

  /**
   * The indexes to the instructions within the method at which the mutation
   * occurs.
   *
   * Usually this will be a single instruction, but may be multiple if the
   * mutation has been inlined by the compiler to implement a finally block
   */
  private final List<Integer> indexes;

  /**
   * The indexes to the instructions within the method at which the mutation
   * occurs for each modification introduced by the mutation.
   *
   * Usually this will be a single instruction per modification, but may be multiple if the
   * mutation has been inlined by the compiler to implement a finally block
   */
  private final List<List<Integer>> indexesList;

  /**
   * Name of the mutation operator that created this mutation
   */
  private final String        mutator;

  /**
   * Name of the mutations operator that created this mutation
   */
  private final List<String> mutators;

  public MutationIdentifier(final Location location, final int index,
      final String mutatorUniqueId) {
    this(location, Collections.singleton(index), mutatorUniqueId);
  }

  public MutationIdentifier(final Location location,
      final Collection<Integer> indexes, final String mutatorUniqueId) {
    this(Collections.singleton(location), Collections.singleton((List<Integer>)new ArrayList<Integer>(indexes)),
            Collections.singleton(mutatorUniqueId));
  }

  public MutationIdentifier(final Collection<Location> locations,
                            final Collection<List<Integer>> indexesList, final Collection<String> mutatorsUniqueIds) {
    this.locations = new ArrayList<>(locations);
    this.location = this.locations.get(0);
    this.indexesList = new ArrayList<>(indexesList);
    this.indexes = this.indexesList.get(0);
    this.mutators = new ArrayList<>(mutatorsUniqueIds);
    this.mutator = this.mutators.get(0);
  }

  /**
   * Returns the location of the mutations
   *
   * @return the location of the mutation
   */
  public Location getLocation() {
    return this.location;
  }

  /**
   * Returns the locations of the mutations
   * @return the locations of the mutation
   */
  public List<Location> getLocations() {
    return this.locations;
  }

  /**
   * Returns the name of the mutator that created this mutation
   *
   * @return the mutator name
   */
  public String getMutator() {
    return this.mutator;
  }

  /**
   * Returns the names of the mutators that created this mutation
   *
   * @return the mutator names
   */
  public List<String> getMutators() {
    return this.mutators;
  }

  /**
   * Returns the list of instruction indexes to which this mutation applies
   *
   * @return the instruction indexes of the mutation
   */
  public List<Integer> getIndexes() {
    return indexesList.get(0);
  }

  /**
   * Returns the index to the first instruction on which this mutation occurs.
   * This index is specific to how ASM represents the bytecode.
   *
   * @return the zero based index to the instruction
   */
  public int getFirstIndex() {
    return this.indexes.iterator().next();
  }

  public List<List<Integer>> getIndexesList() {
    return this.indexesList;
  }

  @Override
  public String toString() {
    return "MutationIdentifier [location=" + this.location + ", indexes="
        + this.indexes + ", mutator=" + this.mutator + "]";
  }

  /**
   * Returns true if this mutation has a matching identifier
   *
   * @param id
   *          the MutationIdentifier to match
   * @return true if the MutationIdentifier matches otherwise false
   */
  public boolean matches(final MutationIdentifier id) {
    //TODO Do that better, only works if id is a FOM identifier
    for (int i = 0; i < this.locations.size(); i++) {
      if (this.locations.get(i).equals(id.location) && this.mutators.get(i).equals(id.mutator)
              && this.indexesList.get(i).contains(id.getFirstIndex())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the class in which this mutation is located
   *
   * @return class in which mutation is located
   */
  public ClassName getClassName() {
    return this.location.getClassName();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result)
        + ((this.indexesList == null) ? 0 : this.indexesList.hashCode());
    result = (prime * result)
        + ((this.locations == null) ? 0 : this.locations.hashCode());
    result = (prime * result)
        + ((this.mutators == null) ? 0 : this.mutators.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MutationIdentifier other = (MutationIdentifier) obj;
    if (this.indexesList == null) {
      if (other.indexesList != null) {
        return false;
      }
    } else if (!this.indexesList.equals(other.indexesList)) {
      return false;
    }
    if (this.locations == null) {
      if (other.locations != null) {
        return false;
      }
    } else if (!this.locations.equals(other.locations)) {
      return false;
    }
    if (this.mutators == null) {
      if (other.mutators != null) {
        return false;
      }
    } else if (!this.mutators.equals(other.mutators)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(final MutationIdentifier other) {
    int comp = this.location.compareTo(other.getLocation());
    if (comp != 0) {
      return comp;
    }
    comp = this.mutator.compareTo(other.getMutator());
    if (comp != 0) {
      return comp;
    }
    return this.indexes.get(0).compareTo(other.indexes.get(0));
  }

}
