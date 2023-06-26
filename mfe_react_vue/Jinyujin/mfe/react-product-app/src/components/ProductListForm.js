import React from 'react'
import { Link } from 'react-router-dom' // imrr

const ProductListForm = ({products, isLoading}) => {
    return (
      <div align="center">
      <h2>React로 만든 상품 게시판</h2>
      { isLoading && "로딩중 ..........." }
      { !isLoading && products && (
        <>
            <Link to="/register">상품 등록</Link>
             <table border="1">
                 <thead>
                     <tr>
                        <th align='center' width="80">
                             번호
                        </th>
                        <th align='center' width="320">
                            상품명
                        </th>
                        <th align='center' width="100">
                            가격
                        </th>
                    </tr>
                </thead>
                <tbody>
                            {!products.length ? (
                        <tr>
                            <td colSpan={4}>
                                등록된 게시물이 존재하지 않습니다!
                            </td>
                        </tr>
                            ) : (products.map((product) => (
                                <tr key={product.productId}>
                                    <td align="center">{product.productId}</td>
                                <td align="left">
                                        <Link to={`/read/${product.productId}`}>{product.productName}</Link>
                                </td>
                                    <td align="right">{product.price}</td>
                        </tr>
                    )))}
                </tbody>
            </table>
        </>
    )}
    </div>
  )
}

export default ProductListForm