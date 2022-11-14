(ns day-sixteen.core
  (:require [clojure.string :refer [split]])
  (:require [clojure.set :refer [union]])
  (:gen-class))

;; 103
(def mistery-sue-attrs
  {:children 3
   :cats 7
   :samoyeds 2
   :pomeranians 3
   :akitas 0
   :vizslas 0
   :goldfish 5
   :trees 3
   :cars 2
   :perfumes 1})

(def input (-> "resources/input.txt" slurp (split #"\n")))

(defn parse-kv [[k v]]
  [(keyword k) (Integer/parseInt v)])

;; input -> "Sue 1: goldfish: 6, trees: 9, akitas: 0"
;; output -> {:sue 1 :attrs {:goldfish 6 :trees 9 :akitas 0}}
(defn parse-sue [input]
  (let [[_ id attr-str] (re-find #"Sue (\d+): (.+)" input)]
    {:sue (Integer/parseInt id)
     :attrs (->> (split attr-str #", ")
                 (map #(split % #": "))
                 (map parse-kv)
                 (into {}))}))

(defn parse-input [i]
  (map parse-sue i))

(defn add-sue-to-lookup [lookup {sue :sue attrs :attrs}]
  (reduce
   #(assoc-in %1 %2 (conj (get-in %1 %2 #{}) sue)) lookup attrs))

(defn build-lookup [sues]
  (reduce add-sue-to-lookup {} sues))

(defn find-sue
  [sues attrs]
  (let [lookup (build-lookup sues)
        candidates (map (partial get-in lookup) attrs)]
    (->> candidates
         (apply union)
         (map (fn [id] {:id id :score (count (filter #(% id) candidates))}))
         (sort-by :score)
         (reverse)
         (first))))

;; (find-sue (parse-input input) mistery-sue-attrs)