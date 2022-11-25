(ns day-nineteen.core
  (:require [clojure.string :as str])
  (:require [clojure.set :as set])
  (:gen-class))

(def input-str (slurp "resources/input.txt"))

(defn parse-mapping-kv [str]
  (str/split str #" => "))

(def input-mappings
  (->> (slurp "resources/input-mappings.txt")
       (str/split-lines)
       (map parse-mapping-kv)
       (into [])))

(defn match-indexes [s substr]
  (filter #(str/starts-with? (subs s %) substr)
          (range (count s))))

(defn replace-str [s pattern replacement start-index]
  (str (subs s 0 start-index)
       (str/replace-first (subs s start-index) pattern replacement)))

(defn replacements [s substr replacement]
  (map #(replace-str s (re-pattern substr) replacement %)
       (match-indexes s substr)))

(defn all-replacements [mappings s]
  (->> mappings
       (map (fn [[pattern replacement]] (replacements s pattern replacement)))
       (flatten)
       (into #{})))

;; part one

(count (all-replacements input-mappings input-str))

;; part two

(defn fewest-num-replacements-to-string [s mappings search-str]
  (loop [values (->> mappings
                     (filter #(== 0 (compare search-str (first %))))
                     (map second)
                     (into #{}))
         level 1]
    (if (contains? values s)
      level
      (recur (apply set/union (map #(all-replacements mappings %) values))
             (inc level)))))

(def test-mappings
  [["e" "H"]
   ["e"  "O"]
   ["H"  "HO"]
   ["H"  "OH"]
   ["O"  "HH"]])

(fewest-num-replacements-to-string "HOH" test-mappings "e")

;; (fewest-num-replacements-to-s input-str input-mappings "e")
