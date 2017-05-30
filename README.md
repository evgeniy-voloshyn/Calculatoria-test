# Calculatoria-test

## Run tests

To run the tests you can use `gradle clean test`. 


## Report

Report will be automatically generated after the run. You can find it in build/allure-report/index.html


## Test data

Test data is stored in config/testdata/testdata.xml 
Before changing anything, please read "Limitations" section.


## Few limitations:

- Please use decimal separator(i.e. '.') for decimals.
- Operations with *big* numbers are not currently supported(e.g. 99999999999^99999999999=Infinity, whitch is not true for BigDecimal).
- POW calculation test supported only for integers.
- For sqrt calcultoin test leave `secondNumber` empty.
