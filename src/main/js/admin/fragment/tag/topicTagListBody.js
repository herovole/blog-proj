import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TextEditingForm} from "../atomic/textEditingForm";
import {SearchTopicTagsInput} from "./searchTopicTagsInput"
import {SearchTopicTagsOutput} from "./searchTopicTagsOutput"

export const TopicTagListBody = ({formKey}) => {
    //prop : formKey
    const [inputCached, setInputCached] = useState(SearchTopicTagsInput.byDefault(formKey, true));
    const [inputFixed, setInputFixed] = useState(SearchTopicTagsInput.byDefault(formKey, true));
    const [output, setOutput] = useState(SearchTopicTagsOutput.empty());
    const [countAddedTags , setCountAddedTags] = useState(0);

    const handlePageChanged = (page) => {
        // Trigger the form submission manually
        inputCached.appendPage(page);
        const form = document.querySelector('form');
        if (form) form.dispatchEvent(new Event('submit', { cancelable: true }));
    }

    useEffect(() => {
        initialLoad();
        return () => {}
    }, []);

    const initialLoad = () => {
        const form = document.querySelector('form');
        if (form) form.dispatchEvent(new Event('submit', { cancelable: true }));
    }

    const handleAddTag = () => {
        setCountAddedTags(countAddedTags + 1);
    }

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload
        const formData = new FormData(event.target);
        const postData = Object.fromEntries(formData.entries());

        try {
            const response = await axios.post("/api/v1/topicTags", postData, {
                headers: { 'Content-Type': 'application/json', },
            });

        } catch (error) {
            console.error('Error submitting form:', error);
            console.error(error.response.data);
            console.error(error.request);
            console.error(error.message);
        }

    }

    const handleSearch = async (event) => {
        event.preventDefault(); // Prevent page reload

        // submitter exists if normal submit button is invoked.
        // submitter is absent if pager is invoked.
        var input;
        if(event.nativeEvent.submitter) {
            input = inputCached;
            setInputFixed({...inputCached});
        } else {
            input = inputFixed.appendPage(inputCached.page);
            setInputCached({...inputFixed});
        }
        console.log("input:", JSON.stringify(input));
        console.log("toUrlSearchParams:", input.toUrlSearchParams().toString());

        try {
            const response = await axios.get("/api/v1/topicTags", {
                params: input.toUrlSearchParams(),
                headers: { Accept: "application/json" },
            });

            console.log(response);
            //console.log(JSON.parse(response));

            //const parsedHash = JSON.parse(jsonString);
            //const searchTopicTagsOutput = await response.data;
            const searchTopicTagsOutput = SearchTopicTagsOutput.fromHash(response.data);
            setOutput(searchTopicTagsOutput);
            console.log("total tagUnits : " + output.articles);
            console.log("tagUnits : " + JSON.stringify(output.tagUnits));

        } catch (error) {
            console.error('Error submitting form:', error);
        }

    }

    const totalPages = () => {
        return output.articles % inputFixed.itemsPerPage == 0
            ? output.articles / inputFixed.itemsPerPage
            : Math.floor(output.articles / inputFixed.itemsPerPage) + 1;
    }

    return (
        <>
            <form onSubmit={handleSearch}>

                <Pagination size="sm" className="pull-right">
                    <Pagination.First onClick={() => handlePageChanged(1)} />
                    <Pagination.Prev onClick={() => handlePageChanged(inputCached.page - 1 > 0 ? inputCached.page - 1 : 1)} />
                    {Array.from({ length: totalPages() }, (_, i) => (
                        <Pagination.Item
                            key={i + 1}
                            active={i + 1 === inputCached.page}
                            onClick={() => handlePageChanged(i + 1)}
                        >
                            {i + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next onClick={() => handlePageChanged(inputCached.page < totalPages() ? inputCached.page + 1 : totalPages())} />
                    <Pagination.Last onClick={() => handlePageChanged(totalPages())} />
                </Pagination>
            </form>

            <form onSubmit={handleSubmit}>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name(JP)</th>
                            <th>NAME(EN)</th>
                            <th>Articles</th>
                            <th>Latest Edit Timestamp</th>
                        </tr>
                    </thead>
                    <tbody>
                {output.tagUnits.tagUnits.map((tagUnit, i) => (
                    <tr>
                        <td>
                            <TextEditingForm
                                postKey={formKey.append(i).append("id")}
                                isFixed={true}
                            >{tagUnit.id}</TextEditingForm>
                        </td>
                        <td>
                            <TextEditingForm
                                postKey={formKey.append(i).append("nameJp")}
                                isFixed={false}
                            >{tagUnit.nameJp}</TextEditingForm>
                        </td>
                        <td>
                            <TextEditingForm
                                postKey={formKey.append(i).append("nameEn")}
                                isFixed={false}
                            >{tagUnit.nameEn}</TextEditingForm>
                        </td>
                        <td>{tagUnit.articles}</td>
                        <td>{tagUnit.lastUpdate}</td>
                    </tr>
                ))}
                {Array.from({ length: countAddedTags }).map((_, i) => (
                    <tr>
                        <td>
                            <TextEditingForm
                                postKey={formKey.append(i).append("id")}
                                isFixed={true}
                            >{null}</TextEditingForm>
                        </td>
                        <td>
                            <TextEditingForm
                                postKey={formKey.append(i).append("nameJp")}
                                isFixed={false}
                            >{null}</TextEditingForm>
                        </td>
                        <td>
                            <TextEditingForm
                                postKey={formKey.append(i).append("nameEn")}
                                isFixed={false}
                            >{null}</TextEditingForm>
                        </td>
                        <td>{"-"}</td>
                        <td>{"new"}</td>
                    </tr>
                ))}
                    </tbody>
                </table>
                <button type="submit">Update</button>
            </form>
            <button type="button" onClick={handleAddTag}>Add</button>
        </>
    );
};
