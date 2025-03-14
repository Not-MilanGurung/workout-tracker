module beds.backend {
    requires transitive java.sql;
	requires bcrypt;

    exports beds.enums;
    exports beds.backend;
	exports beds.database;
	exports beds.security;
}
