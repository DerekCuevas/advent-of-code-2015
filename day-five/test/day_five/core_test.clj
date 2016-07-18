(ns day-five.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split]]
            [day-five.core :refer :all]))

(def input (split (slurp "resources/input.txt") #"\n"))

(deftest nice-count-one-test
  (testing "returns number of nice strings in list"
    (is (= (nice-count-one input) 238))))

(deftest nice-count-two-test
  (testing "returns number of nice strings in list"
    (is (= (nice-count-two input) 69))))

