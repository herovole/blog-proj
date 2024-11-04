import React from 'react';
import Select from 'react-select';
import {TagUnitList} from './tagUnitList.js';

export class TagSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.candidates, // :TagUnitList
            selectedTags : this.props.selectedTagIds ? this.props.selectedTagIds : [],
            fixedSelectedTags : this.props.selectedTagIds ? this.props.selectedTagIds : [],
            isBeingEdited : false
        };
        console.log("state / " + this.props.candidates);
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

    handleChange = (theSelectedTags) => {
        var ids = theSelectedTags.map(e => parseInt(e.value,10));
        this.setState(prevState => ({
            selectedTags: ids
        }));
    };

    render() {
        if(this.state.isBeingEdited) {
            console.log("render-active :" + JSON.stringify(this.props.candidates.getTagOptionsJapanese()));
            console.log("selected :" + JSON.stringify(this.state.selectedTags));
            return (
                <div>
                    <Select
                      isMulti
                      options={this.props.candidates.getTagOptionsJapanese()}
                      value={this.props.candidates.getTagOptionsJapaneseSelected(this.state.selectedTags)}
                      onChange={this.handleChange}
                      placeholder="Select or type to add tags"
                    />
                    <Select
                      isMulti
                      options={this.props.candidates.getTagOptionsEnglish()}
                      value={this.props.candidates.getTagOptionsEnglishSelected(this.state.selectedTags)}
                      onChange={this.handleChange}
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
            console.log("render-passive:" + JSON.stringify(this.props.candidates.getTagOptionsJapanese()));
            var tagsInJapanese = this.props.candidates.getJapaneseNamesByIdsForDisplay(this.state.fixedSelectedTags);
            var tagsInEnglish = this.props.candidates.getEnglishNamesByIdsForDisplay(this.state.fixedSelectedTags);
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
