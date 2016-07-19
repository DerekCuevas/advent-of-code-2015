(ns day-six.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split]]
            [day-six.core :refer :all]))

(defonce ^:private input (split (slurp "resources/input.txt") #"\n"))

(deftest total-lights-on-test
  (testing "returns number of lights on in grid after processing instructions"
    (is (= (total-lights-on input) 569999))))

(deftest total-brightness-test
  (testing "returns total brightness of lights on in grid after processing instructions"
    (is (= (total-brightness input) 17836115))))
