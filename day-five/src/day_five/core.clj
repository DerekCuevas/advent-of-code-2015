(ns day-five.core
  (:require [clojure.string :refer [includes?]])
  (:gen-class))

(def ^:private not-empty? (comp not empty?))

(defn- contains-at-least-three-vowels? [s]
  (>= (count (filter #{\a \e \i \o \u} s)) 3))

(defn- any-twice-in-a-row? [s]
  (not= (count s) (count (dedupe s))))

(defn- not-in-blacklist? [s]
  (->> ["ab" "cd" "pq" "xy"]
       (map (partial includes? s))
       (not-any? true?)))

(defn- pair-appears-twice? [s]
  (some? (re-find #"([a-z][a-z]).*\1" s)))

(defn- one-letter-repeats-with-one-between? [s]
  (->> (partition 3 1 s)
       (filter #(= (first %) (last %)))
       not-empty?))

(defn- nice? [rules s]
  (every? true? (rules s)))

(defn nice-count [rules coll]
  (count (filter (partial nice? rules) coll)))

(def ^:private rules-one
  (juxt contains-at-least-three-vowels?
        any-twice-in-a-row?
        not-in-blacklist?))

(defn nice-count-one [coll]
  (nice-count rules-one coll))

(def ^:private rules-two
  (juxt pair-appears-twice?
        one-letter-repeats-with-one-between?))

(defn nice-count-two [coll]
  (nice-count rules-two coll))
