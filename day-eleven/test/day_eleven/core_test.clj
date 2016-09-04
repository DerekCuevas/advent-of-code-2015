(ns day-eleven.core-test
  (:require [clojure.test :refer :all]
            [day-eleven.core :refer :all]))

(def input (slurp "resources/input.txt"))

(deftest next-password-test
  (testing "next password"
    (is (= (next-password "hxbxwxba") "hxbxxyzz"))))

(deftest next-next-password-test
  (testing "next next password"
    (is (= (next-password "hxbxxyzz") "hxcaabcc"))))
