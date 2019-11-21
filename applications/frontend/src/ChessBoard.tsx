import * as React from 'react'
import { ChessTile } from './ChessTile'
import { List } from "immutable"


export class ChessBoard extends React.Component<{}, {}> {
    public render() {
        return (
            <>
                {List(new Array<number>(64)).map((v, i) => <ChessTile key={i} />)}
            </>
        )
    }
}