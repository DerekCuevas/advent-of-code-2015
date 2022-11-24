(ns day-nineteen.core
  (:require [clojure.string :as str])
  (:gen-class))

(def input-str (slurp "resources/input.txt"))

(defn parse-mapping-kv [str]
  (str/split str #" => "))

(def input-mappings
  (->> (slurp "resources/input-mappings.txt")
       (str/split-lines)
       (map parse-mapping-kv)
       (into [])))

(defn starts-with? [vec-a vec-b]
  (every? identity (map #(== 0 (compare %1 %2)) vec-a vec-b)))

(defn match-indexes [subs s]
  (let [v (vec s)
        subv (vec subs)]
    (filter #(starts-with? (subvec v %) subv) (range (count v)))))

(defn replace-str [s pattern replacement start-index]
  (str (subs s 0 start-index)
       (str/replace-first (subs s start-index) pattern replacement)))

(defn all-replacements [s pattern replacement]
  (map #(replace-str s (re-pattern pattern) replacement %) (match-indexes pattern s)))

;; (def test-str "abcdabeabfg")

;; (replace-str test-str #"ab" "cd" 4)

;; (all-replacements test-str "ab" "cd")

(->> input-mappings
     (map (fn [[pattern replacement]] (all-replacements input-str pattern replacement)))
     (flatten)
     (into #{})
     (count))
