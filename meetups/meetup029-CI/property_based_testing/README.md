# _QuickCheck your facts_ by [Nikolas Vourlakis](https://github.com/Archimidis)

This talk is about __property-based testing__ using [__QuickCheck__](https://en.wikipedia.org/wiki/QuickCheck).
In this folder, you will find the slides used during the presentation. All referenced papers as well as additional resources are included.

The paper that introduced QuickCheck is [QuickCheck: A Lightweight Tool for Random Testing of Haskell Programs](./papers/QuickCheck.pdf). This the one that won [the most influential ICFP paper award](http://www.sigplan.org/Awards/ICFP/#2010_Koen_Claessen_and_John_Hughes).

In the paper [Finding Race Conditions in Erlang with QuickCheck and PULSE](./papers/Finding_Race_Conditions_in_Erlang_with_QuickCheck_and_PULSE.pdf), it is described how to test and debug concurrent, distributed Erlang applications. There a related talk based on this paper and the [here are the slides](./QuickCheck_your_facts_(SLIDES).pdf).

#### John Hughes - Testing the Hard Stuff and Staying Sane
* [Video](https://www.youtube.com/watch?v=zi0rHwfiX1Q)
* [Paper](./papers/Experiences_With_QuickCheck.pdf)

#### AUTOSAR Use Case
* [Thomas Arts - Testing AUTOSAR components with QuickCheck (Video)](https://vimeo.com/26085628)
* [John Hughes: Certifying your car with Erlang (Video)](https://vimeo.com/68331689)
* [Testing AUTOSAR software with QuickCheck (Paper)](./papers/Testing_AUTOSAR_software_with_QuickCheck.pdf)

#### LevelDB Use Case
* [Quick check test suite (Erlang)](https://github.com/norton-archive/lets/blob/master/test/qc/qc_statemc_lets.erl)
* [Issue details](http://code.google.com/p/leveldb/issues/detail?id=44)

#### Riak Use Case
* [QuickChecking Riak (Video)](https://skillsmatter.com/skillscasts/4505-quickchecking-riak)
* [QuickChecking Poolboy for Fun and Profit (Blog post)](http://basho.com/posts/technical/quickchecking-poolboy-for-fun-and-profit/)
