package se.paap.solutionrecyclerviewexercise.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class User implements Parcelable {
        private static final int DEFAULT_ID = -1;

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

        private User(Parcel in) {
            username = in.readString();
            age = in.readInt();
            id = in.readLong();
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

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.username);
            dest.writeInt(this.age);
            dest.writeLong(this.id);
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
