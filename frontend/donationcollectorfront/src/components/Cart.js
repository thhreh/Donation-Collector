import { Button, Drawer, List, message, Typography } from "antd";
import { useEffect, useState } from "react";
import { checkout, getCart } from "../utils";

import emailjs from 'emailjs-com';
import{ init } from '@emailjs/browser';



const { Text } = Typography;

const MyCart = () => {
    const [cartVisible, setCartVisible] = useState(false);
    const [cartData, setCartData] = useState();
    const [loading, setLoading] = useState(false);
    const [checking, setChecking] = useState(false);
    //const [email, setemail] = useState();
    const [Date, setDate] = useState();

    useEffect(() => {
        if (!cartVisible) {
            return;
        }

        setLoading(true);
        getCart()
            .then((data) => {
                setCartData(data);
            })
            .catch((err) => {
                message.error(err.message);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [cartVisible]);

    const sendemail = async () => {
        const temp_email = await checkout()
        for(let val1 of Object.keys(temp_email)) {
            emailjs.send("service_4oedpgp","template_awd4qpk",{
                to_name: temp_email[val1].username,
                Pickup_time: Date,
            });
        }
    }

    const onCheckOut = () => {
        setChecking(true);
        try{
            sendemail()
            message.success("Successfully checkout");
            setCartVisible(false);
        }
        catch (err) {
            message.error(err.message);
        }
        finally {
            setChecking(false);
        };
    };

    const onCloseDrawer = () => {
        setCartVisible(false);
    };

    const onOpenDrawer = () => {
        setCartVisible(true);
    };

    return (
        <>
            <Button type="primary" shape="round" onClick={onOpenDrawer}>
                Cart
            </Button>
            <Drawer
                title="Pickup List"
                onClose={onCloseDrawer}
                visible={cartVisible}
                width={520}
                footer={
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "space-between",
                        }}
                    >
                        <input type="text" placeholder="Expect Pickup Date" onChange={e => setDate(e.target.value)} />
                        <Text strong={true}>{`Total Weight: ${cartData?.totalWeight} KG`}</Text>
                        <div>
                            <Button onClick={onCloseDrawer} style={{ marginRight: 8 }}>
                                Cancel
                            </Button>
                            <Button
                                onClick={onCheckOut}
                                type="primary"
                                loading={checking}
                                disabled={loading || cartData?.cartItemList.length === 0}
                            >
                                Place pick up
                            </Button>
                        </div>
                    </div>
                }
            >
                <List
                    loading={loading}
                    itemLayout="horizontal"
                    dataSource={cartData?.cartItemList}
                    renderItem={(Cartitem) => (
                        <List.Item>
                            <List.Item.Meta
                                title={Cartitem.item.name}
                                description={`${Cartitem.item.weight}KG`}
                            />
                        </List.Item>
                    )}
                />
            </Drawer>
        </>
    );
};

export default MyCart;