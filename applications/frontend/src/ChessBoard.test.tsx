import * as React from 'react'
import { shallow, ShallowWrapper } from 'enzyme'
import { ChessBoard } from './ChessBoard'
import { ChessTile } from './ChessTile'

describe("a chess board", () => {
    it('should render', () => {
        const component = shallow(<ChessBoard />)
        expect(component.exists()).toBe(true)
    })

    it('should have a grid of 8x8 cells', () => {
        const component = shallow(<ChessBoard />)
        expect(component.find(ChessTile)).toHaveLength(64)
    })
})