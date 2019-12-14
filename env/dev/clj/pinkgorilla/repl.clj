(ns pinkgorilla.repl
  (:require
    [clojure.tools.logging :refer (info)]
    [clojure.pprint :refer [pprint]]
    [shadow.cljs.devtools.api :as shadow]
    [shadow.cljs.devtools.server.nrepl :as shadow-nrepl]
    [shadow.cljs.devtools.server :as shadow-server]
    [nrepl.server :as nrepl :refer [start-server stop-server]]
    [cider.nrepl :as cider-nrepl]

    [pinkgorilla.core :as core]
    ;; [pinkgorilla.system :as gsys]
    [pinkgorilla.cli :as cli]))

;; (def system (atom (sys/create-figwheel-system cfg)))
;; (def system (atom (sys/figwheel-system cfg)))

#_(component/start @system)

#_(component/start-system @system)

;; (start-figwheel! cfg)
;(pinkgorilla.replikativ/start-replikativ)

#_(def figwheel-server
    (:figwheel-server @(get-in figwheel-sidecar.repl-api/*repl-api-system* [:figwheel-system :system])))

#_(defn start-figwheel-component []
    ;; (component/start figwheel-server)
    ;; (swap! system component/start)
    )

#_(defn stop-figwheel-component []
    ;; (component/stop figwheel-server)
    ;; (swap! system component/stop)
    )

#_(defn reload []
    (print "Reload all the things")
    ;; (stop)
    ;; (start)
    )

;; (defonce server (atom nil))

;; (start-figwheel! (config))

;; (ra/print-config)
;; (cljs-repl)

#_(defn start-server
    "used for starting the server in development mode from REPL"
    [& [port]]
    (let [port (if port (Integer/parseInt port) 3000)]
      (reset! server
              (serve (get-handler)
                     {:port         port
                      :auto-reload? true
                      :join?        false}))
      (println (str "You can view the site at http://localhost:" port))))

#_(defn stop-server []
    (.stop @server)
    (reset! server nil))

#_(cljs.build.api/build "src"
                        {:main      'hello-world.core
                         :output-to "out/main.js"
                         :verbose   true})

#_(defn start-cljs-repl
    []
    (pback/cljs-repl (cljs.repl.node/repl-env)
                     :watch "in"
                     ;; :reader
                     :output-dir "out"))

#_(defn run-cljs
    []
    (core/run-gorilla-server {})
    (start-cljs-repl))

#_(go
    (cljs.repl/repl (cljs.repl.node/repl-env)
                    ;; :watch "in"
                    :reader
                    :output-dir "out"))



(def cljs-build :app-with-cljs-kernel-dev)
(def gorilla-default-cli-config {:port 9000})
(def gorilla-system (atom nil))

(defn start-system
  ([]
   (start-system gorilla-default-cli-config))
  ([gorilla-config]
   (reset! gorilla-system (core/run-gorilla-server gorilla-config))))

;; TODO: Ugly workaround
(defn -main
  {:shadow/requires-server true}
  [& args]
  (info "dev.pinkgorilla.repl.-main starting with args: " args)
  (defonce nrepl-server (start-server :port 4001
                                      :handler (apply nrepl/default-handler
                                                      (map resolve (into cider-nrepl/cider-middleware
                                                                         ['shadow.cljs.devtools.server.nrepl/middleware])))))
  (let [{:keys [options arguments errors summary]} (cli/parse-opts args)]
    (start-system options))
  (shadow-server/start!)
  (shadow/watch cljs-build {:verbose true})
  ;; (start "dev")
  )

(comment (+ 1 1)
         (shadow/repl cljs-build))
