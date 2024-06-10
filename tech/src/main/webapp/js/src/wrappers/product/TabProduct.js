import PropTypes from "prop-types";
import React, { useEffect, useState } from "react";
// import { Link } from "react-router-dom";
import Tab from "react-bootstrap/Tab";
import Nav from "react-bootstrap/Nav";
import SectionTitle from "../../components/section-title/SectionTitle";
import ProductGrid from "./ProductGrid";
import WebService from '../../util/webService';
import constant from '../../util/constant';
import { setLoader } from "../../redux/actions/loaderActions";
import { multilanguage } from "redux-multilanguage";
import { connect } from "react-redux";
const TabProduct = ({
  setLoader,
  spaceTopClass,
  spaceBottomClass,
  category,
  containerClass,
  extraClass,
  defaultStore,
  currentLanguageCode
}) => {
  // const [featuredData, setFeaturedData] = useState([]);
  const [categoryData, setCategoryData] = useState([]);
  useEffect(() => {
    getProductList();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  const getProductList = async () => {
    setLoader(true)
    let action = constant.ACTION.PRODUCT_GROUP + 'FEATURED_ITEM?store=' + defaultStore + '&lang=' + currentLanguageCode;
    try {
      let response = await WebService.get(action);
      // console.log(response);
      if (response) {
        let category = [{ 'id': '', 'name': 'All', 'code': 'all', data: response.products }];
        response.products.forEach((item) => {
          if (item.categories !== null) {
            item.categories.forEach((a) => {
              // console.log(a)
              let index = category.findIndex(value => value.id === a.id);
              // console.log(index);
              if (index === -1) {
                category.push({ 'id': a.description.id, 'name': a.description.name, 'code': a.code, data: [item] })
              } else {
                category[index].data.push(item)
              }
            })
          }
        });
        // setFeaturedData(response.products)
        setCategoryData(category)
        setLoader(false)
      }
    } catch (error) {
      setLoader(false)
    }
  }
  return (
    <div
      className={`product-area ${spaceTopClass ? spaceTopClass : ""} ${
        spaceBottomClass ? spaceBottomClass : ""
        } ${extraClass ? extraClass : ""}`}
    >
      <div className={`${containerClass ? containerClass : "container"}`}>
        <SectionTitle titleText="Featured Products" positionClass="text-center" />
        <Tab.Container defaultActiveKey="all">
          <Nav variant="pills" className="product-tab-list pt-30 pb-55 text-center">
            {
              categoryData.map((value, i) => {
                return (
                  <Nav.Item key={i} >
                    <Nav.Link eventKey={value.code}>
                      <h4>{value.name}</h4>
                    </Nav.Link>
                  </Nav.Item>)
              })
            }
          </Nav>
          <Tab.Content>
            {
              categoryData.map((value, i) => {
                return (
                  <Tab.Pane key={i} eventKey={value.code}>
                    <div className="row">
                      <ProductGrid
                        products={value.data}
                        type="men"
                        limit={8}
                        spaceBottomClass="mb-25"
                      />
                    </div>
                  </Tab.Pane>
                )
              })
            }
          </Tab.Content>
        </Tab.Container>
      </div>
    </div>
  );
};

TabProduct.propTypes = {
  category: PropTypes.string,
  containerClass: PropTypes.string,
  extraClass: PropTypes.string,
  spaceBottomClass: PropTypes.string,
  spaceTopClass: PropTypes.string,
  setLoader: PropTypes.func
};


const mapStateToProps = state => {
  return {
    currentLanguageCode: state.multilanguage.currentLanguageCode,
    defaultStore: state.merchantData.defaultStore
  };
};
const mapDispatchToProps = dispatch => {
  return {
    setLoader: (value) => {
      dispatch(setLoader(value));
    }
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(multilanguage(TabProduct));
// export default TabProductNine;
