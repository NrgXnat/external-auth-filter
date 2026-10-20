# XNAT External Authentication Filter Plugin

This is the repository for the XNAT external authentication filter plugin. It provides the ability to
authenticate in the XNAT desktop client through the XNAT site the user is trying to access, rather than
entering username and password in the desktop client UI. This allows users to log into sites that use
an external authentication provider such as Active Directory or OpenID.
## Build

To build the XNAT external authentication filter plugin, run the following command:

```bash
./gradlew clean xnatPluginJar
```

The plugin jar file will be located in the `build/libs` directory.


To build the documentation locally, run the following command:

```bash
sphinx-build sphinx-build -b html docs/source docs/build 
```

