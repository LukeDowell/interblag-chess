import * as React from "react"

import { shallow, ShallowWrapper } from 'enzyme'
import { ChessTile } from "./ChessTile"

describe("a chess tile", () => {
  
    it("should render", () => {
        const component = shallow(<ChessTile />)
        expect(component.exists()).toBe(true)
    })
})