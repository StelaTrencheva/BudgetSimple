import React from 'react';
import { Navbar,Container,Nav } from 'react-bootstrap'

const Field = (props) => {
    return (
        <div className="row">
            <div className="col-sm-3">
                <h6 className="mb-0">{props.label}</h6>
            </div>
            <div className="col-sm-9 text-secondary">
                <input disabled={!props.edit} onChange={(e) => props.onChange(e.target.value)} value = {props.value}></input>
            </div>
        </div>
    );
}
export default Field
