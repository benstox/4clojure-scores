(def url "https://www.4clojure.com/user")

(def friends '("benstox" "kevinmarsh3" "alexhenman" "eduardo_gomez" "val_camp"))

(defn download_page [username]
    (slurp (str url "/" username)))

(defn find_score_text [username]
    (re-find
        #"<td class=\"count-value\">([0-9]+)/"
        (download_page username)))

