PIT-HOM is an extension of Pitest for Higher order mutation.

Pitest (aka PIT) is a state of the art mutation testing system for Java and the JVM.

Read all about it at http://pitest.org


##Using PIT-HOM
PIT-HOM can be used exactly like Pitest (see [here](http://pitest.org/quickstart/). It adds two options to Pitest's configuration: the "hom" option, that takes a list of orders of mutation to run (defaults to "1"), and the "mutantProcessingMethod" that specifies how mutants are run (can take values "all", "stream" or "stream-batch", defaults to "all").

## Credits

Pitest is mainly the work of [Henry Coles](https://twitter.com/0hjc) but has benefited from contributions from many others. 

Notable contributions not visible [here](https://github.com/hcoles/pitest/graphs/contributors) as they were made before this code was migrated to github include 

* Nicolas Rusconi - Ant Task
* Struan Kerr-Liddell - Improvements to html report
* Stephan Pendorf - Multiple improvments including improved mutators
 
Although PIT does not incorporate any code from the Jumble project (http://jumble.sourceforge.net/), the Jumble codebase was used as a guide when developing some aspects of PIT.



