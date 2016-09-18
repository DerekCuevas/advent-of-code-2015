(ns day-fourteen.core
  (:gen-class))

(def ^:private reindeer-format
  #"(\S+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.")

(defn- init-reindeer [name speed duration rest]
  {:name name
   :speed speed
   :rest rest
   :duration duration
   :points 0
   :position 0
   :resting? false
   :time-awake 0
   :time-asleep 0})

(defn- parse-reindeer [s]
  (let [[_ name speed duration rest] (re-find reindeer-format s)]
    (apply init-reindeer
           (keyword name)
           (map read-string [speed duration rest]))))

(defn- asleep [reindeer]
  (update reindeer :time-asleep inc))

(defn- move [reindeer]
  (-> reindeer
      (update :time-awake inc)
      (update :position + (:speed reindeer))))

(defn- awake [reindeer]
  (-> reindeer
      (assoc :resting? false)
      (assoc :time-asleep 0)))

(defn- sleep [reindeer]
  (-> reindeer
      (update :position + (:speed reindeer))
      (assoc :resting? true)
      (assoc :time-awake 0)))

(defn- should-awake? [{:keys [rest resting? time-asleep]}]
  (and resting? (= (inc time-asleep) rest)))

(defn- should-sleep? [{:keys [duration resting? time-awake]}]
  (and (not resting?) (= (inc time-awake) duration)))

(defn- tick [reindeer]
  (cond
    (should-awake? reindeer)
      (awake reindeer)
    (should-sleep? reindeer)
      (sleep reindeer)
    (not (:resting? reindeer))
      (move reindeer)
    (:resting? reindeer)
      (asleep reindeer)))

(defn- award-point [leading reindeer]
  (if (not= leading (:position reindeer))
    reindeer
    (update reindeer :points inc)))

(defn- award-points [participants]
  (let [sorted (sort-by :position > participants)
        leading (:position (first sorted))]
    (map (partial award-point leading) participants)))

(defn- race [duration participants]
  (->> (range duration)
       (reduce
        (fn [reindeer _]
          (award-points (map tick reindeer)))
        participants)))

;; part one
(defn fastest-reindeer-by-position [duration input]
  (->> (map parse-reindeer input)
       (race duration)
       (map :position)
       (apply max)))

;; part two
(defn fastest-reindeer-by-points [duration input]
  (->> (map parse-reindeer input)
       (race duration)
       (map :points)
       (apply max)))
