Refactoring Discussion
====

## Duplication Refactoring
Our team does not have any significant issues with duplicated code, so we decided to focus on other areas for refactoring.

## Checklist Refactoring
* We refactored import statements so that any unused ones were deleted and any that imported too much so that we only used what we needed.
* We also refactored constants so that they don't appear as magic values. Instead, the constants are now stored in private static final variables.
* We also made any public instance variables we found private.

## General Refactoring
* We deleted some variables that the program pointed out that we were not using, such as the variable "name" in the class SliderUI.

## Longest Method Refactoring
* We made a helper method to help make one of our longer methods updateGrid() in the SegGrid class more understandable.