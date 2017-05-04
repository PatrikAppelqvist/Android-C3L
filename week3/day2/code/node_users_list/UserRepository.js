const usersMap = {};

function* createIdGen() {
    let i = 1;
    while(true) {
        yield i++;
    }
}

const idGenerator = createIdGen();

const getUsers = (query) => {
    const users = Object.keys(usersMap).map(userId => usersMap[userId]);

    if(!query) {
        return users;
    }

    return users.filter(user => {
        return  user.username.toLowerCase().includes(query.toLowerCase()) || 
                user.age.toString().toLowerCase().includes(query.toLowerCase());
    });
}

const getUser = (id) => {
    return usersMap[id];
};

const updateUser = (id, user) => {
    user.id = id;
    usersMap[id] = user;
};

const addUser = (user) => {
    if(user && user.username && user.age) {
        if((typeof user.username === 'string') && (typeof user.age === 'number')) {
            user.id = idGenerator.next().value;
            usersMap[user.id] = user;

            return user;
        }
    } 
    
    return null;
};

const removeUser = (id) => {
    delete usersMap[id];
};

module.exports = {
    addUser, updateUser, getUsers, getUser, removeUser
};