(ns day-seven.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split]]
            [day-seven.core :refer :all]))

(def input (split (slurp "resources/input.txt") #"\n"))

(def circuit-one (build-circuit input))
(def circuit-two (assoc circuit-one :b 16076))

(deftest probe-test
  (testing "returns signal on wire in circuit"
    (is (= (probe circuit-one :a) 16076))
    (is (= (probe circuit-two :a) 2797))))
