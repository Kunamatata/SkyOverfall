class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'home',
            action: 'index')

        "/profile"(controller: 'profile',
                   action: 'index')

        "/user/$name"(controller: 'user', action: 'profile')
        "/user/list"(controller: 'user', action: 'list')
        "/user/searchUser"(controller: 'user', action: 'searchUser')

        "/user/createUser"(controller: 'user', action: 'createUser')

        "500"(view:'/error')
        "404"(view:'/notFound')


        "/login/$action?"(controller: "login")
        "/logout/$action?"(controller: "logout")
        "/login/auth"(controller: "home", action:"index")


    }
}
