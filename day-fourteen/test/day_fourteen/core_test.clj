(ns day-fourteen.core-test
  (:require [clojure.test :refer :all]
            [day-fourteen.core :refer :all]
            [clojure.string :refer [split]]))

(def input (split (slurp "resources/input.txt") #"\n"))

(deftest fastest-reindeer-test
  (testing "fastest-reindeer"
    (is (= (fastest-reindeer 2503 input) 2696))))
