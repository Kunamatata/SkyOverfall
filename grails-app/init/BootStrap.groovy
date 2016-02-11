import skyoverfall.*

class BootStrap {

    def init = { servletContext ->

      def adminRole = Role.findOrSaveByAuthority('ROLE_ADMIN')
      def userRole = Role.findOrSaveByAuthority('ROLE_USER')

      String username = 'admin'

      if (!User.findByUsername(username)) {
         def testUser = new User(username: username, enabled: true,
                                    password: 'adminadmin')

         testUser.save()

         UserRole.create testUser, adminRole, true
        }
    }

    def destroy = {
    }
}
