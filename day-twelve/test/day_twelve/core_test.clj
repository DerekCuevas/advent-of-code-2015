(ns day-twelve.core-test
  (:require [clojure.test :refer :all]
            [day-twelve.core :refer :all]
            [clojure.data.json :refer [read-str]]))

(def input (read-str (slurp "resources/input.json")))

(deftest sum-test
  (testing "sums all numbers in json"
    (is (= (sum input) 191164))))

(deftest sum-without-red-test
  (testing "sums all numbers in json, except for objects and children with key 'red'"
    (is (= (sum-without-red input) 87842))))
