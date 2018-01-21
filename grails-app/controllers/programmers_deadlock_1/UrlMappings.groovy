package programmers_deadlock_1

class UrlMappings {

    static mappings = {
        post "/add_data"(controller: 'application', action: 'addData')
        get "/last_blocks"(controller: 'application', action: 'lastBlocks', properties: 'count')

        "/"(controller: 'application', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
