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

(defn match-indexes [s substr]
  (filter #(str/starts-with? (subs s %) substr)
          (range (count s))))

(defn replace-str [s pattern replacement start-index]
  (str (subs s 0 start-index)
       (str/replace-first (subs s start-index) pattern replacement)))

(defn all-replacements [s substr replacement]
  (map #(replace-str s (re-pattern substr) replacement %) (match-indexes s substr)))

;; (def test-str "abcdabeabfg")

;; (match-indexes test-str "ab")

;; (replace-str test-str #"ab" "cd" 4)

;; (all-replacements test-str "ab" "cd")

(->> input-mappings
     (map (fn [[pattern replacement]] (all-replacements input-str pattern replacement)))
     (flatten)
     (into #{})
     (count))
