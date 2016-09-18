(ns day-fourteen.core
  (:gen-class))

(def ^:private reindeer-format
  #"(\S+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.")

(defn- init-reindeer [speed duration rest]
  {:speed speed
   :duration duration
   :rest rest
   :points 0
   :position 0
   :resting? false
   :time-flying 0
   :time-resting 0})

(defn- parse-reindeer [s]
  (let [[_ _ & constants] (re-find reindeer-format s)]
    (apply init-reindeer (map read-string constants))))

(defn- resting [reindeer]
  (update reindeer :time-resting inc))

(defn- flying [reindeer]
  (-> reindeer
      (update :time-flying inc)
      (update :position + (:speed reindeer))))

(defn- resting->flying [reindeer]
  (-> reindeer
      (assoc :resting? false)
      (assoc :time-resting 0)))

(defn- flying->resting [reindeer]
  (-> reindeer
      (update :position + (:speed reindeer))
      (assoc :resting? true)
      (assoc :time-flying 0)))

(defn- should-fly? [{:keys [rest resting? time-resting]}]
  (and resting? (= (inc time-resting) rest)))

(defn- should-rest? [{:keys [duration resting? time-flying]}]
  (and (not resting?) (= (inc time-flying) duration)))

(defn- tick [reindeer]
  (cond
    (should-fly? reindeer)
      (resting->flying reindeer)
    (should-rest? reindeer)
      (flying->resting reindeer)
    (not (:resting? reindeer))
      (flying reindeer)
    (:resting? reindeer)
      (resting reindeer)))

(defn- award-point [leading {:keys [position] :as reindeer}]
  (if-not (= leading position)
    reindeer
    (update reindeer :points inc)))

(defn- award-points [participants]
  (let [[leading] (sort-by :position > participants)]
    (map (partial award-point (:position leading)) participants)))

(defn- race [duration participants]
  (loop [participants participants
         time-elapsed 0]
    (if (>= time-elapsed duration)
      participants
      (recur (award-points (map tick participants))
             (inc time-elapsed)))))

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
