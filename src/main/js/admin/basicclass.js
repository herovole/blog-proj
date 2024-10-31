import React from 'react';

console.log("basicclass.js");


export class BasicClass extends React.Component {
  constructor() {
    super();
    this.state = {color: "red"};
  }
  render() {
    return <h2>I am a Car!</h2>;
  }
}

