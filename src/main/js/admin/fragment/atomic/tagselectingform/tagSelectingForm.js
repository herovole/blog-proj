import React from 'react';
import Select from 'react-select';

export class TagSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.candidates, // :TagUnitList
            //this.props.allowsMultipleOptions
            //this.props.isFixed
            selectedTags: this.props.selectedTagIds ? Array.isArray(this.props.selectedTagIds) ? this.props.selectedTagIds : [this.props.selectedTagIds] : [],
            fixedSelectedTags: this.props.selectedTagIds ? Array.isArray(this.props.selectedTagIds) ? this.props.selectedTagIds : [this.props.selectedTagIds] : [],
            isBeingEdited: false
        };
        console.log("state / " + this.props.candidates);
    }

    componentDidMount() {
    }

    edit = () => {
        if (this.props.isFixed) return;
        this.setState(prevState => ({
            isBeingEdited: true
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedSelectedTags: this.state.selectedTags,
            isBeingEdited: false
        }));
    }

    cancel = () => {
        this.setState(prevState => ({
            selectedTags: this.state.fixedSelectedTags,
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
        if (this.state.isBeingEdited) {
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
                        onClick={this.cancel}
                    >
                        Cancel
                    </button>
                </div>
            );
        } else {
            console.log("render-passive:" + JSON.stringify(this.props.candidates.getTagOptionsJapanese()));
            const tagsInJapanese = this.props.candidates.getJapaneseNamesByIdsForDisplay(this.state.fixedSelectedTags);
            const tagsInEnglish = this.props.candidates.getEnglishNamesByIdsForDisplay(this.state.fixedSelectedTags);
            return (
                <div onClick={this.edit}>
                    <div className="editable-text-fixed">
                        {tagsInJapanese ? tagsInJapanese : "(None)"}
                    </div>
                    <div className="editable-text-fixed">
                        {tagsInEnglish ? tagsInEnglish : "(None)"}
                    </div>
                    {this.state.fixedSelectedTags.map((v, i) => (
                        <input type="hidden"
                               name={this.props.postKey.append(i).toStringKey()}
                               value={v}/>
                    ))}
                </div>
            );
        }
    }

}
