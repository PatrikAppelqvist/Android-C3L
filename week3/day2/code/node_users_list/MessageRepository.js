const messages = [];

const addMessage = (message) => {
    messages.push(message);
};

const getMessages = () => {
    return messages;
};

module.exports = {
    addMessage, getMessages
};