import React from 'react';

export class IdsEditingForm extends React.Component {

    static SPLITTER = ",";

    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.isFixed : forbidden to edit
            ids: this.props.ids ? Array.isArray(this.props.ids) ? this.props.ids : [this.props.ids] : [],
            fixedIds: this.props.ids ? Array.isArray(this.props.ids) ? this.props.ids : [this.props.ids] : [],
            isBeingEdited: false,
        };
    }

    componentDidMount() {
    }


    handleChange = (e) => {
        const currentText = e.target.value;
        this.setState(prevState => ({
            ids: currentText.split(IdsEditingForm.SPLITTER)
        }));
    }

    edit = () => {
        this.setState(prevState => ({
            isBeingEdited: !this.props.isFixed
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedIds: this.state.ids,
            isBeingEdited: false
        }));
    }

    cancel = () => {
        this.setState(prevState => ({
            ids: this.state.fixedIds,
            isBeingEdited: false
        }));
    }

    render() {
        if (this.state.isBeingEdited) {
            return (
                <div>
                    <input
                        className="editable-text-activated scale-span"
                        pattern="^[0-9,]*$"
                        placeholder="comma separated integer ids"
                        value={this.state.ids.join(IdsEditingForm.SPLITTER)}
                        onChange={this.handleChange}
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
            return (
                <div onClick={this.edit}>
                    <div className="editable-text-fixed scale-span">
                        {this.state.fixedIds.length > 0 ? this.state.fixedIds.join(IdsEditingForm.SPLITTER) : "(No IDs)"}
                    </div>
                    {this.state.fixedIds.map((v, i) => (
                        <input type="hidden"
                               name={this.props.postKey.append(i).toStringKey()}
                               value={v}/>
                    ))}
                </div>
            );
        }
    }
}

