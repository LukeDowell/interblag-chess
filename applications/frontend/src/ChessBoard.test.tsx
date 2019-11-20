import * as React from 'react'
import { shallow, ShallowWrapper } from 'enzyme'
import { ChessBoard } from './ChessBoard'

describe("a chess board", () => {
    let component: ShallowWrapper

    beforeEach(() => {
        component = shallow(<ChessBoard />)
    })

    it('should render', () => {
        expect(component.exists()).toBe(true)
    }) 
})