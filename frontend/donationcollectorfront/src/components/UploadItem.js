import React from "react";
import { Form, Input, InputNumber, Button, message,TreeSelect} from "antd";
import { uploadItem } from "../utils"; //upload item
import emailjs from 'emailjs-com';

const layout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 16 }
};

class UploadItem extends React.Component {
    state = {
        loading: false
    };

    fileInputRef = React.createRef();

    UploadandCheck = async(formData) => {
        const ngoemails = await uploadItem(formData);

        console.log(ngoemails)
        if (ngoemails.length !== 0){
            for(let val of Object.keys(ngoemails)) {
                console.log(val)
                if(ngoemails[val].username.includes("gmail") || ngoemails[val].username.includes("Gmail")){
                    console.log(ngoemails[val].username)
                    emailjs.send("service_4oedpgp","template_ih0mx1h",{
                        to_name: ngoemails[val].username,
                    });
                }
            }
        }
    }

    handleSubmit = async (values) => {
        const formData = new FormData();
        const { files } = this.fileInputRef.current;

        if (files.length > 5) {
            message.error("You can at most upload 5 pictures.");
            return;
        }

        for (let i = 0; i < files.length; i++) {
            formData.append("images", files[i]);
        }

        formData.append("name", values.name);
        formData.append("description", values.description);
        formData.append("category", values.category);
        formData.append("donor", values.donor);
        formData.append("weight", values.weight);

        this.setState({
            loading: true
        });
        try {
            this.UploadandCheck(formData)
            message.success("upload successfully");

        } catch (error) {
            message.error(error.message);
        } finally {
            this.setState({
                loading: false
            });
        }
    };

    render() {
        return (
            <Form className="upload"
                  {...layout}
                  name="nest-messages"
                  onFinish={this.handleSubmit}
                  style={{ maxWidth: 2000, margin: "auto" }}
            >
                <Form.Item name="name" label="Item Name" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item
                    name="description"
                    label="Description"
                    rules={[{ required: true }]}
                >
                    <Input.TextArea autoSize={{ minRows: 2, maxRows: 6 }} />
                </Form.Item>
                <Form.Item name="category" label="Category" rules={[{ required: true }]}>
                    <TreeSelect
                        treeData={[
                            {
                                title: 'Food',
                                value: 'Food',
                            },
                            {
                                title: 'Clothes',
                                value: 'Clothes',
                            },
                            {
                                title: 'Electronics',
                                value: 'Electronics',
                            }
                        ]}
                    />
                </Form.Item>
                <Form.Item
                    name="weight"
                    label="Weight"
                    rules={[{ required: true, type: "number", min: 0 }]}
                >
                    <InputNumber />
                </Form.Item>
                <Form.Item name="images" label="Image" rules={[{ required: true }]}>
                    <input
                        type="file"
                        accept="image/png, image/jpeg"
                        ref={this.fileInputRef}
                        multiple={true}
                    />
                </Form.Item>
                <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                    <Button type="primary" htmlType="submit" loading={this.state.loading}>
                        Submit
                    </Button>
                </Form.Item>
            </Form>
        );
    }
}

export default UploadItem;