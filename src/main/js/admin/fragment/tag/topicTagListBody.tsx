import React, {useEffect, useRef, useState} from 'react';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TextEditingForm} from "../atomic/textEditingForm";
import {SearchTagsInput} from "../../../service/tags/searchTagsInput";
import {TagService} from "../../../service/tags/tagService";
import {SearchTagsOutput} from "../../../service/tags/searchTagsOutput";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {ElementId} from "../../../domain/elementId/elementId";

type TopicTagListBodyProps = {
    postKey: ElementId;
}

export const TopicTagListBody: React.FC<TopicTagListBodyProps> = ({postKey}) => {
    //prop : formKey
    const [output, setOutput] = useState(SearchTagsOutput.empty());
    const [countAddedTags, setCountAddedTags] = useState(0);

    const searchForm = useRef<HTMLFormElement>(null);
    const tagService: TagService = new TagService();
    const [page, setPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(50);


    const handlePageChanged = (page: number) => {
        setPage(page);
    }
    const handleItemsPerPageChanged = (itemsPerPage: number) => {
        setItemsPerPage(itemsPerPage);
    }

    useEffect(() => {
        initialLoad();
    }, []);

    const initialLoad = () => {
        if (searchForm.current) {
            searchForm.current.dispatchEvent(
                new Event('submit', {cancelable: true, bubbles: true})
            );
        }
    }

    const handleAddTag = () => {
        setCountAddedTags(countAddedTags + 1);
    }

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload
        const formData = new FormData(event.target as HTMLFormElement);
        const result: BasicApiResult = await tagService.editTopicTags(formData);
        if (result.isSuccessful()) {
            setCountAddedTags(0);
            initialLoad();
        }

    }

    const handleSearch = async (event) => {
        event.preventDefault(); // Prevent page reload
        const input: SearchTagsInput = new SearchTagsInput(page, itemsPerPage, true);
        const output: SearchTagsOutput = await tagService.searchTopicTags(input);
        if (output.isSuccessful()) {
            setCountAddedTags(0);
            initialLoad();
        }
        setOutput(output);
    }

    const totalPages = () => {
        const totalNumber: number = output.getTotalNumber();
        return totalNumber % itemsPerPage === 0
            ? totalNumber / itemsPerPage
            : Math.floor(totalNumber / itemsPerPage) + 1;
    }

    return (
        <>
            <form ref={searchForm} onSubmit={handleSearch}>

                <Pagination size="sm" className="pull-right">
                    <Pagination.First onClick={() => handlePageChanged(1)}/>
                    <Pagination.Prev
                        onClick={() => handlePageChanged(page - 1 > 0 ? page - 1 : 1)}/>
                    {Array.from({length: totalPages()}, (_, i) => (
                        <Pagination.Item
                            key={i + 1}
                            active={i + 1 === page}
                            onClick={() => handlePageChanged(i + 1)}
                        >
                            {i + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next
                        onClick={() => handlePageChanged(page < totalPages() ? page + 1 : totalPages())}/>
                    <Pagination.Last onClick={() => handlePageChanged(totalPages())}/>
                </Pagination>
            </form>

            <form onSubmit={handleSubmit}>
                <input type="hidden" name="requiresAuth" value={1}/>
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
                    {output.getTagUnits().tagUnits.map((tagUnit, i) => (
                        <tr>
                            <td>
                                <TextEditingForm
                                    postKey={postKey.append(i.toString()).append("id")}
                                    isFixed={true}
                                >{tagUnit.fields.id}</TextEditingForm>
                            </td>
                            <td>
                                <TextEditingForm
                                    postKey={postKey.append(i.toString()).append("nameJp")}
                                    isFixed={false}
                                >{tagUnit.fields.tagJapanese}</TextEditingForm>
                            </td>
                            <td>
                                <TextEditingForm
                                    postKey={postKey.append(i.toString()).append("nameEn")}
                                    isFixed={false}
                                >{tagUnit.fields.tagEnglish}</TextEditingForm>
                            </td>
                            <td>{tagUnit.fields.articles}</td>
                            <td>{tagUnit.fields.lastUpdate}</td>
                        </tr>
                    ))}
                    {Array.from({length: countAddedTags}).map((_, i) => (
                        <tr>
                            <td>
                                <TextEditingForm
                                    postKey={postKey.append(i.toString()).append("id")}
                                    isFixed={true}
                                >{null}</TextEditingForm>
                            </td>
                            <td>
                                <TextEditingForm
                                    postKey={postKey.append(i.toString()).append("nameJp")}
                                    isFixed={false}
                                >{null}</TextEditingForm>
                            </td>
                            <td>
                                <TextEditingForm
                                    postKey={postKey.append(i.toString()).append("nameEn")}
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
