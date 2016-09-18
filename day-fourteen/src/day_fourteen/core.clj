(ns day-fourteen.core
  (:gen-class))

(def ^:private reindeer-format #"(\S+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.")

(defn- init-reindeer [name speed duration rest]
  {:name name
   :speed speed
   :rest rest
   :duration duration
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

(defn- race [duration participants]
  (->> (range duration)
       (reduce (fn [reindeer _] (doall (map tick reindeer))) participants)))

(defn fastest-reindeer [duration input]
  (->> (map parse-reindeer input)
       (race duration)
       (map :position)
       (apply max)))
