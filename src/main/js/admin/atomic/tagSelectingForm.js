import React from 'react';
import axios from 'axios';
import Select from 'react-select';
import {TagUnitList} from '../../domain/tagUnitList.js';

export class TagSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            candidates: TagUnitList.empty(), // :TagUnitList
            selectedTags : this.props.selectedTagIds ? this.props.selectedTagIds : [],
            fixedSelectedTags : this.props.selectedTagIds ? this.props.selectedTagIds : [],
            isBeingEdited : false
        };
    }

    componentDidMount() { }

    switchMode = () => {
        this.setState(prevState => ({
            isBeingEdited: !prevState.isBeingEdited
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedSelectedTags: this.state.selectedTags,
            isBeingEdited: false
        }));
    }

    replenishOptions= async () => {
        try {
            var response = await axios.post("/b/api/tags");
            this.setState(prevState => ({
                candidates: TagUnitList.fromJsonStringList(response.data),
            }));
        } catch (error) {
            console.error("error : tagSelectingForm", error);
        }
    };

    render() {
        if(this.state.isBeingEdited) {
            this.replenishOptions();
            return (
                <div>
                    <Select
                      isMulti
                      options={this.state.candidates.getOptionsJapanese}
                      value={this.state.selectedTags}
                      //onChange={handleTagChange}
                      placeholder="Select or type to add tags"
                    />
                    <Select
                      isMulti
                      options={this.state.candidates.getOptionsEnglish}
                      value={this.state.selectedTags}
                      //onChange={handleTagChange}
                      placeholder="Select or type to add tags"
                    />
                    <button
                      type="button"
                      onClick={this.fix}
                    >
                        Fix
                    </button>
                    <button
                      type="button"
                      onClick={this.switchMode}
                    >
                        Cancel
                    </button>
                </div>
            );
        } else {
            this.replenishOptions();
            var tagsInJapanese = this.state.candidates.getJapaneseNamesByIdsForDisplay(this.state.fixedSelectedTags);
            var tagsInEnglish = this.state.candidates.getEnglishNamesByIdsForDisplay(this.state.fixedSelectedTags);
            return (
                <div onClick={this.switchMode} >
                    <div class="editable-text-fixed">
                        {tagsInJapanese}
                    </div>
                    <div class="editable-text-fixed">
                        {tagsInEnglish}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey}
                      value={JSON.stringify(this.state.fixedSelectedTags)} />
                </div>
            );
        }
    }

}
