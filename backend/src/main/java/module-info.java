module beds.backend {
    requires transitive java.sql;
	requires transitive javafx.base;
	requires at.favre.lib.bcrypt;

    exports beds.enums;
    exports beds.backend;
	exports beds.database;
	exports beds.security;
}
