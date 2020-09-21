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
package org.pitest.testapi;

import org.pitest.rewriter.Serializer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author henry
 *
 */
public final class TestResult {

  private final Description   description;

  private final Throwable     throwable;
  private final TestUnitState state;

  public TestResult(final Description description, final Throwable t) {
    this(description, t, TestUnitState.FINISHED);
  }

  public TestResult(final Description description, final Throwable t,
      final TestUnitState state) {
    this.description = description;
    this.throwable = t;
    this.state = state;
  }

  public String getFailureMessage(){
    StackTraceElement[] frames = this.throwable.getStackTrace();// printStackTrace(pr);
    Set<String> set = new HashSet<String>();
    String res = this.throwable.toString();
    res = res + " " + Serializer.STRACE;
    for (StackTraceElement frame : frames) {
      // if(frame.isNativeMethod())
      // break;
      String line = frame.toString();
      if (!set.contains(line)) {
        res += " " + line;
        set.add(line);
      }
    }
    set.clear();
    return res.replaceAll("\n", " ").replaceAll("\r", " ");
  }

  public Throwable getThrowable() {
    return this.throwable;
  }

  public TestUnitState getState() {
    return this.state;
  }

  public Description getDescription() {
    return this.description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result)
        + ((this.description == null) ? 0 : this.description.hashCode());
    result = (prime * result)
        + ((this.state == null) ? 0 : this.state.hashCode());
    result = (prime * result)
        + ((this.throwable == null) ? 0 : this.throwable.hashCode());
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
    final TestResult other = (TestResult) obj;
    if (this.description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!this.description.equals(other.description)) {
      return false;
    }
    if (this.state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!this.state.equals(other.state)) {
      return false;
    }
    if (this.throwable == null) {
      if (other.throwable != null) {
        return false;
      }
    } else if (!this.throwable.equals(other.throwable)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "TestResult [description=" + this.description + ", state="
        + this.state + ", throwable=" + this.throwable + "]";
  }

}
