import React from 'react';
import Select from 'react-select';
import {TagUnitList} from './tagUnitList.js';
import {ElementId} from '../../../../domain/elementId'

export class TagSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.candidates, // :TagUnitList
            //this.props.allowsMultipleOptions
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
        console.log("selected handleChange : " + JSON.stringify(theSelectedTags));

        const thoseSelectedTags = this.props.allowsMultipleOptions ? theSelectedTags : [theSelectedTags];
        const ids = thoseSelectedTags.map(e => e.value);
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
                      isMulti={this.props.allowsMultipleOptions}
                      options={this.props.candidates.getTagOptionsJapanese()}
                      value={this.props.candidates.getTagOptionsJapaneseSelected(this.state.selectedTags)}
                      onChange={this.handleChange}
                      placeholder="Select or type to add tags"
                    />
                    <Select
                      isMulti={this.props.allowsMultipleOptions}
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
            const tagsInJapanese = this.props.candidates.getJapaneseNamesByIdsForDisplay(this.state.fixedSelectedTags);
            const tagsInEnglish = this.props.candidates.getEnglishNamesByIdsForDisplay(this.state.fixedSelectedTags);
            const fixedSelectedTags = Array.isArray(this.state.fixedSelectedTags) ? this.state.fixedSelectedTags : [];
            return (
                <div onClick={this.switchMode} >
                    <div class="editable-text-fixed">
                        {tagsInJapanese}
                    </div>
                    <div class="editable-text-fixed">
                        {tagsInEnglish}
                    </div>
                    {fixedSelectedTags.map((v, i) => (
                        <input type="hidden"
                          name={this.props.postKey.append(i).toStringKey()}
                          value={v} />
                    ))}
                </div>
            );
        }
    }

}
