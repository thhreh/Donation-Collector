const domain = "http://localhost:8080";


export const signup = (credential, asNGO) => {
    const signupUrl = `${domain}/register/${asNGO ? "ngo" : "donor"}`;
    return fetch(signupUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(credential),
    }).then((response) => {
        if (response.status !== 200) {
            throw Error("Fail to sign up");
        }
    });
};

export const login = (credential, asNGO) => {
    //credential 用户and密码
    const loginUrl = `${domain}/authenticate/${asNGO ? "ngo" : "donor"}`; // string template: es6
    return fetch(loginUrl, {
        //fetch: input parameter (target location, 配置)
        method: "POST",
        headers: {
            "Content-Type": "application/json", //tell the body is actually  json
        },
        body: JSON.stringify(credential), //json object-->string
    }).then((response) => {
        //this response is generated by website
        if (response.status !== 200) {
            // see the status results returned by background
            throw Error("Fail to log in");
        }

        return response.json();
    }); //then里的内容在request成功返回response后执行，else if 断网 or 404 不执行,被catch掉,这里的catch写在了业务层
};

export const getItemById = (itemId) => {
    const authToken = localStorage.getItem("authToken");
    const getItemUrl = `${domain}/items/`;
    return fetch(getItemUrl, {
        headers: {
            Authorization: `Bearer ${authToken}`
        }
    }).then((response) => {
        if (response.status !== 200) {
            throw Error("Fail to get your items list");
        }
        return response.json();
    });
};

export const deleteItem = (itemId) => {
    const authToken = localStorage.getItem("authToken");
    const deleteItemUrl = `${domain}/items/${itemId}`;

    return fetch(deleteItemUrl, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${authToken}`
        }
    }).then((response) => {
        if (response.status !== 200) {
            throw Error("Fail to delete item");
        }
    });
};

export const getItemsByDonor = () => {
    const authToken = localStorage.getItem("authToken");
    const listItemsUrl = `${domain}/items/`;

    return fetch(listItemsUrl, {
        headers: {
            Authorization: `Bearer ${authToken}`
        }
    }).then((response) => {
        if (response.status !== 200) {
            throw Error("Fail to get items list");
        }

        return response.json();
    });
};

export const uploadItem = (data) => {
    const authToken = localStorage.getItem("authToken");
    const uploadItemUrl = `${domain}/items`;

    return fetch(uploadItemUrl, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${authToken}`
        },
        body: data
    }).then((response) => {
        if (response.status !== 200) {
            throw Error("Fail to upload item");
        }
        return response.json();
    });
};
export const searchCatagory = (query) => {
    const authToken = localStorage.getItem("authToken");
    const searchStaysUrl = new URL(`${domain}/search`);
    searchStaysUrl.searchParams.append("category", query);

    return fetch(searchStaysUrl, {
        headers: {
            Authorization: `Bearer ${authToken}`
        }
    }).then((response) => {
        if (response.status !== 200) {
            throw Error("Fail to search catagories");
        }
        return response.json();
    });
};

export const getCart = () => {
    const authToken = localStorage.getItem("authToken");
    const getCartUrl = new URL (`${domain}/cart/`);

    return fetch(getCartUrl, {
        headers: {
            Authorization: `Bearer ${authToken}`
        }
    }).then((response) => {
        if (response.status < 200  || response.status >= 300) {
            throw Error("Fail to get shopping cart data");
        }
        return response.json();
    });
};

export const checkout = () => {
    const authToken = localStorage.getItem("authToken");
    const getCheckouttUrl = new URL(`${domain}/schedule`);
    return fetch(getCheckouttUrl, {
        method:"DELETE",
        headers: {
            Authorization: `Bearer ${authToken}`
        }
    }).then((response) => {
        if (response.status < 200  || response.status >= 300) {
            throw Error("Fail to checkout");
        }
        return response.json();
    });
};
export const addToCart = (itemId) => {
    const authToken = localStorage.getItem("authToken");
    const getAddOrderUrl = new URL(`${domain}/order/${itemId}`);
    return fetch(getAddOrderUrl, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${authToken}`,
        }
    }).then((response) => {
        if (response.status < 200 || response.status >= 300) {
            throw Error("Fail to add menu item to shopping cart");
        }
    });
};
