
/*
 * Many applications need to hold data about their users locally, even if
 * authentication is delegated to an external provider. We don’t show the code
 * here, but it is easy to do in two steps.
 * 
 * Choose a backend for your database, and set up some repositories (using
 * Spring Data, say) for a custom User object that suits your needs and can be
 * populated, fully or partially, from external authentication.
 * 
 * Implement and expose OAuth2UserService to call the Authorization Server as
 * well as your database. Your implementation can delegate to the default
 * implementation, which will do the heavy lifting of calling the Authorization
 * Server. Your implementation should return something that extends your custom
 * User object and implements OAuth2User.
 * 
 * Hint: add a field in the User object to link to a unique identifier in the
 * external provider (not the user’s name, but something that’s unique to the
 * account in the external provider).
 */