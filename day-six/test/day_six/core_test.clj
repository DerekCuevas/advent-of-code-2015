(ns day-six.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split]]
            [day-six.core :refer :all]))

(defonce ^:private input (split (slurp "resources/input.txt") #"\n"))

(deftest count-lights-on-test
  (testing "returns number of lights on in grid after processing instructions"
    (is (= (count-lights-on input) 569999))))
