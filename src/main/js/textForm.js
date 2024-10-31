
import React from 'react';

    /**
     * props
     *   name
     *   placeholder
    **/
	class TextForm extends React.Component {
	  constructor(props) {
        super(props);
      }

	  render() {
		return (
		  <div className="textForm">
		    <textarea name={this.props.name} placeholder={this.props.placeholder}></textarea>
		  </div>
		);
	  }
	}

export default TextForm;