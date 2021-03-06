(ns fourclojure-scores.core
    (:require [environ.core :refer [env]])
    (:require [clojure.string :as str]))

(def url "https://www.4clojure.com/user")

(defn download_page [username]
    (slurp (str url "/" username)))

(defn find_score_text [username]
    (last
        (re-find
            #"<td class=\"count-value\">([0-9]+)/"
            (download_page username))))

(defn find_longest_length [usernames]
    (reduce max (map count usernames)))

(defn formatted_name_score
    ([longest_length username]
            (str "# "
                 (format (str "%-" longest_length "s") username)
                 " - "
                 (format "%5s" (find_score_text username))
                 " #"))
    ([longest_length username score]
            (str "# "
                 (format (str "%-" longest_length "s") username)
                 " - "
                 (format "%5s" score)
                 " #")))

(defn format_all [longest_length usernames]
    (map (partial formatted_name_score longest_length) usernames))

(defn display [usernames]
    (let [longest_length (find_longest_length usernames)]
        (let [formatted (format_all longest_length usernames)]
            (println (formatted_name_score longest_length "Username" "Score"))
            (loop [next_up (first formatted) the_rest (rest formatted)]
                (println next_up)
                (if (empty? the_rest)
                    nil
                    (recur (first the_rest) (rest the_rest)))))))

(defn -main [] (display (str/split (env :friends) #",")))
