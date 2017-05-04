package se.paap.networkuserlistapplication.model;

public final class User {
    private static final long DEFAULT_ID = -1;

    private final String username;
        private final int age;
        private final long id;

        public User(long id, String username, int age) {
            this.id = id;
            this.username = username;
            this.age = age;
        }

        public User(String username, int age) {
            this(DEFAULT_ID, username, age);
        }

        public boolean hasBeenPersisted() {
            return id != DEFAULT_ID;
        }

        public String getUsername() {
            return username;
        }

        public int getAge() {
            return age;
        }

        public long getId() {
            return id;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            if (age != user.age) return false;

            return username.equals(user.username) && id != user.id;
        }

        @Override
        public int hashCode() {
            int result = username.hashCode();
            result = 31 * result + age;
            result = 31 * result + (int) (id ^ (id >>> 32));

            return result;
        }
}
