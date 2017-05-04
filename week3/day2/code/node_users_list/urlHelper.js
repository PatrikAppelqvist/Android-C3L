const getQueryParamsObj = (queryParams) => {
    if(queryParams == null || !(typeof queryParams === 'string')) {
        return {};
    }

    const pairs = queryParams.split('&');
    return pairs.reduce((prev, curr) => {
        const keyValue = curr.split('=').map(string => decodeURIComponent(string));
        prev[keyValue[0]] = keyValue[1];

        return prev;
    }, {});
};

module.exports = {
    getQueryParamsObj, 
};