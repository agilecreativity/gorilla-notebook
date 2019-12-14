(ns pinkgorilla.cli
  (:require
     [clojure.tools.cli :as cli]
     [clojure.string :as string]))

;; Shamelessly stolen from
;; https://github.com/gorillalabs/gorilla-repl/blob/develop/src/gorilla_repl/cli.clj

(def cli-options                                            ;; see https://github.com/clojure/tools.cli#example-usage
  ;; An option with a required argument
  (let [glob-expression #"[0-9\?\*\[\]\-\^\{\}\\\,]*"
        env-expression #"[0-9a-zA-Z\-\.]*"
        ]
    [#_["-s" "--standalone" "Start nrepl server inside GorillaREPL process."
        :id :standalone
        ]

     ["-p" "--nrepl-port PORT" "Port number of the nrepl server."
      :default nil
      :parse-fn #(Integer/parseInt %)
      :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]

     ["-h" "--nrepl-host HOST" "host of the nrepl server."
      :default nil
      ;; Specify a string to output in the default column in the options summary
      ;; if the default value's string representation is very ugly
      :default-desc "none"]

     ["-P" "--port PORT" "Port number of the GorillaREPL server."
      :default 9000
      :parse-fn #(Integer/parseInt %)
      :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]

     ["-H" "--ip IP" "IP of the GorillaREPL server."
      :default "localhost"
      ;; Specify a string to output in the default column in the options summary
      ;; if the default value's string representation is very ugly
      :default-desc "localhost"]
     ["-c" "--runtime-config runtime-config" "Runtime config edn file"
      :default nil
      ]
     ;; [nil "--project PROJECT" "name of the project."]

     [nil "--help"]]))

(defn usage [options-summary]
  (->> ["GorillaREPL, can use an embedded nrepl-sever (standalone) or connect to an existing one."
        ""
        ""
        "Options:"
        options-summary
        ""
        ""
        ]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))


(defn parse-opts [args]
  (let [{:keys [options arguments errors summary] :as parsed} (cli/parse-opts args cli-options)]

    (cond
      (:help options) (exit 0 (usage summary))
      ;(not= (count arguments) 1) (exit 1 (usage summary))
      (not-empty errors) (exit 1 (error-msg errors)))
    parsed))
